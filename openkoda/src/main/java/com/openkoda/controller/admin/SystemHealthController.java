/*
MIT License

Copyright (c) 2016-2023, Openkoda CDX Sp. z o.o. Sp. K. <openkoda.com>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
documentation files (the "Software"), to deal in the Software without restriction, including without limitation
the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice
shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.openkoda.controller.admin;

import com.openkoda.App;
import com.openkoda.core.flow.Flow;
import com.openkoda.core.security.HasSecurityRules;
import com.openkoda.model.component.FrontendResource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.openkoda.controller.common.URLConstants._HTML;

@RestController
@RequestMapping(_HTML)
public class SystemHealthController extends AbstractSystemHealthController implements HasSecurityRules {

    @PreAuthorize(CHECK_CAN_READ_SUPPORT_DATA)
    @GetMapping(_SYSTEM_HEATH)
    public Object systemHealth() {
        debug("[systemHealth]");
        return getSystemHealth()
                .mav("system-health");
    }

    @PreAuthorize(CHECK_CAN_READ_SUPPORT_DATA)
    @GetMapping(_SYSTEM_HEATH + _VALIDATE)
    public Object validateDatabase() {
        debug("[validateDatabase]");
        return validate()
                .mav("system-health::database-validation");
    }

    @PreAuthorize(CHECK_CAN_READ_BACKEND)
    @GetMapping(_THREAD)
    public Object threads() {
        debug("[threads]");
        return getThreads()
                .mav("threads");
    }

    @PreAuthorize(CHECK_CAN_MANAGE_BACKEND)
    @PostMapping(_THREAD_ID_INTERRUPT)
    public Object threadInterrupt(@PathVariable(ID) Long threadId) {
        debug("[threadInterrupt]");
        return interruptThread(threadId)
                .mav("threads");
    }

    @PreAuthorize(CHECK_CAN_MANAGE_BACKEND)
    @PostMapping(_THREAD_ID_REMOVE)
    public Object threadRemove(@PathVariable(ID) Long threadId) {
        debug("[threadInterrupt]");
        return removeThread(threadId)
                .mav("threads");
    }

    @PreAuthorize(CHECK_CAN_MANAGE_BACKEND)
    @GetMapping(_DASHBOARD)
    public Object adminDashboard(@Qualifier("obj") Pageable pageable) {
        return Flow.init()
                .thenSet(frontendResourcePage, a -> repositories.unsecure.frontendResource.findByResourceType(FrontendResource.ResourceType.UI_COMPONENT,pageable))
                .execute()
                .mav("admin-dashboard");

    }

    @PreAuthorize(CHECK_CAN_MANAGE_BACKEND)
    @GetMapping(_COMPONENTS)
    public Object components() {
        return Flow.init()
                .execute()
                .mav("components");

    }

    @PreAuthorize(CHECK_CAN_MANAGE_BACKEND)
    @GetMapping("/restart")
    public Object restart() {
        App.restart();
        return null;

    }

}
