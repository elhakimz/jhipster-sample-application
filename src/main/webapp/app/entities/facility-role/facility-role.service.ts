import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFacilityRole } from 'app/shared/model/facility-role.model';

type EntityResponseType = HttpResponse<IFacilityRole>;
type EntityArrayResponseType = HttpResponse<IFacilityRole[]>;

@Injectable({ providedIn: 'root' })
export class FacilityRoleService {
  public resourceUrl = SERVER_API_URL + 'api/facility-roles';

  constructor(protected http: HttpClient) {}

  create(facilityRole: IFacilityRole): Observable<EntityResponseType> {
    return this.http.post<IFacilityRole>(this.resourceUrl, facilityRole, { observe: 'response' });
  }

  update(facilityRole: IFacilityRole): Observable<EntityResponseType> {
    return this.http.put<IFacilityRole>(this.resourceUrl, facilityRole, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFacilityRole>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFacilityRole[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
