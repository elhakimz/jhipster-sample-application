import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICasusRole } from 'app/shared/model/casus-role.model';

type EntityResponseType = HttpResponse<ICasusRole>;
type EntityArrayResponseType = HttpResponse<ICasusRole[]>;

@Injectable({ providedIn: 'root' })
export class CasusRoleService {
  public resourceUrl = SERVER_API_URL + 'api/casus-roles';

  constructor(protected http: HttpClient) {}

  create(casusRole: ICasusRole): Observable<EntityResponseType> {
    return this.http.post<ICasusRole>(this.resourceUrl, casusRole, { observe: 'response' });
  }

  update(casusRole: ICasusRole): Observable<EntityResponseType> {
    return this.http.put<ICasusRole>(this.resourceUrl, casusRole, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICasusRole>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICasusRole[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
