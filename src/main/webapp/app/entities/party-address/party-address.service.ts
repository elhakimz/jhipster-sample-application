import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPartyAddress } from 'app/shared/model/party-address.model';

type EntityResponseType = HttpResponse<IPartyAddress>;
type EntityArrayResponseType = HttpResponse<IPartyAddress[]>;

@Injectable({ providedIn: 'root' })
export class PartyAddressService {
  public resourceUrl = SERVER_API_URL + 'api/party-addresses';

  constructor(protected http: HttpClient) {}

  create(partyAddress: IPartyAddress): Observable<EntityResponseType> {
    return this.http.post<IPartyAddress>(this.resourceUrl, partyAddress, { observe: 'response' });
  }

  update(partyAddress: IPartyAddress): Observable<EntityResponseType> {
    return this.http.put<IPartyAddress>(this.resourceUrl, partyAddress, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPartyAddress>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPartyAddress[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
