import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPartyContact } from 'app/shared/model/party-contact.model';

type EntityResponseType = HttpResponse<IPartyContact>;
type EntityArrayResponseType = HttpResponse<IPartyContact[]>;

@Injectable({ providedIn: 'root' })
export class PartyContactService {
  public resourceUrl = SERVER_API_URL + 'api/party-contacts';

  constructor(protected http: HttpClient) {}

  create(partyContact: IPartyContact): Observable<EntityResponseType> {
    return this.http.post<IPartyContact>(this.resourceUrl, partyContact, { observe: 'response' });
  }

  update(partyContact: IPartyContact): Observable<EntityResponseType> {
    return this.http.put<IPartyContact>(this.resourceUrl, partyContact, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPartyContact>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPartyContact[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
