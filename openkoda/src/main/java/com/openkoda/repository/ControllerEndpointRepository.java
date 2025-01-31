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

package com.openkoda.repository;

import com.openkoda.core.repository.common.UnsecuredFunctionalRepositoryWithLongId;
import com.openkoda.core.security.HasSecurityRules;
import com.openkoda.model.OpenkodaModule;
import com.openkoda.model.component.ControllerEndpoint;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ControllerEndpointRepository extends UnsecuredFunctionalRepositoryWithLongId<ControllerEndpoint>, HasSecurityRules, ComponentEntityRepository<ControllerEndpoint> {

    List<ControllerEndpoint> findByFrontendResourceId(long frontendResourceId);

    ControllerEndpoint findByFrontendResourceIdAndSubPathAndHttpMethod(long frontendResourceId, String subPath, ControllerEndpoint.HttpMethod httpMethod);
//    ControllerEndpoint findByFrontendResourceIdAndSubPathAndHttpMethodAndOrganizationId(long frontendResourceId, String subPath, ControllerEndpoint.HttpMethod httpMethod, long organizationId);

    @Modifying
    @Query("delete from ControllerEndpoint where module = :module")
    void deleteByModule(OpenkodaModule module);
}
