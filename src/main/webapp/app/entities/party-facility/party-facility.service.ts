import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPartyFacility } from 'app/shared/model/party-facility.model';

type EntityResponseType = HttpResponse<IPartyFacility>;
type EntityArrayResponseType = HttpResponse<IPartyFacility[]>;

@Injectable({ providedIn: 'root' })
export class PartyFacilityService {
  public resourceUrl = SERVER_API_URL + 'api/party-facilities';

  constructor(protected http: HttpClient) {}

  create(partyFacility: IPartyFacility): Observable<EntityResponseType> {
    return this.http.post<IPartyFacility>(this.resourceUrl, partyFacility, { observe: 'response' });
  }

  update(partyFacility: IPartyFacility): Observable<EntityResponseType> {
    return this.http.put<IPartyFacility>(this.resourceUrl, partyFacility, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPartyFacility>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPartyFacility[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
