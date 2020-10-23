import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContactMechLink } from 'app/shared/model/contact-mech-link.model';

type EntityResponseType = HttpResponse<IContactMechLink>;
type EntityArrayResponseType = HttpResponse<IContactMechLink[]>;

@Injectable({ providedIn: 'root' })
export class ContactMechLinkService {
  public resourceUrl = SERVER_API_URL + 'api/contact-mech-links';

  constructor(protected http: HttpClient) {}

  create(contactMechLink: IContactMechLink): Observable<EntityResponseType> {
    return this.http.post<IContactMechLink>(this.resourceUrl, contactMechLink, { observe: 'response' });
  }

  update(contactMechLink: IContactMechLink): Observable<EntityResponseType> {
    return this.http.put<IContactMechLink>(this.resourceUrl, contactMechLink, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContactMechLink>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContactMechLink[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
