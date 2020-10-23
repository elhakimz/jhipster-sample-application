import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFacilityRoleType } from 'app/shared/model/facility-role-type.model';

type EntityResponseType = HttpResponse<IFacilityRoleType>;
type EntityArrayResponseType = HttpResponse<IFacilityRoleType[]>;

@Injectable({ providedIn: 'root' })
export class FacilityRoleTypeService {
  public resourceUrl = SERVER_API_URL + 'api/facility-role-types';

  constructor(protected http: HttpClient) {}

  create(facilityRoleType: IFacilityRoleType): Observable<EntityResponseType> {
    return this.http.post<IFacilityRoleType>(this.resourceUrl, facilityRoleType, { observe: 'response' });
  }

  update(facilityRoleType: IFacilityRoleType): Observable<EntityResponseType> {
    return this.http.put<IFacilityRoleType>(this.resourceUrl, facilityRoleType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFacilityRoleType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFacilityRoleType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
