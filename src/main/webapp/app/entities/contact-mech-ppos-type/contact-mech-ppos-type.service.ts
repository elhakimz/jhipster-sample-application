import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContactMechPposType } from 'app/shared/model/contact-mech-ppos-type.model';

type EntityResponseType = HttpResponse<IContactMechPposType>;
type EntityArrayResponseType = HttpResponse<IContactMechPposType[]>;

@Injectable({ providedIn: 'root' })
export class ContactMechPposTypeService {
  public resourceUrl = SERVER_API_URL + 'api/contact-mech-ppos-types';

  constructor(protected http: HttpClient) {}

  create(contactMechPposType: IContactMechPposType): Observable<EntityResponseType> {
    return this.http.post<IContactMechPposType>(this.resourceUrl, contactMechPposType, { observe: 'response' });
  }

  update(contactMechPposType: IContactMechPposType): Observable<EntityResponseType> {
    return this.http.put<IContactMechPposType>(this.resourceUrl, contactMechPposType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContactMechPposType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContactMechPposType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
