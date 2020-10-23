import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFacilityContactMech } from 'app/shared/model/facility-contact-mech.model';

type EntityResponseType = HttpResponse<IFacilityContactMech>;
type EntityArrayResponseType = HttpResponse<IFacilityContactMech[]>;

@Injectable({ providedIn: 'root' })
export class FacilityContactMechService {
  public resourceUrl = SERVER_API_URL + 'api/facility-contact-meches';

  constructor(protected http: HttpClient) {}

  create(facilityContactMech: IFacilityContactMech): Observable<EntityResponseType> {
    return this.http.post<IFacilityContactMech>(this.resourceUrl, facilityContactMech, { observe: 'response' });
  }

  update(facilityContactMech: IFacilityContactMech): Observable<EntityResponseType> {
    return this.http.put<IFacilityContactMech>(this.resourceUrl, facilityContactMech, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFacilityContactMech>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFacilityContactMech[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
