import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICommEvtPposType } from 'app/shared/model/comm-evt-ppos-type.model';

type EntityResponseType = HttpResponse<ICommEvtPposType>;
type EntityArrayResponseType = HttpResponse<ICommEvtPposType[]>;

@Injectable({ providedIn: 'root' })
export class CommEvtPposTypeService {
  public resourceUrl = SERVER_API_URL + 'api/comm-evt-ppos-types';

  constructor(protected http: HttpClient) {}

  create(commEvtPposType: ICommEvtPposType): Observable<EntityResponseType> {
    return this.http.post<ICommEvtPposType>(this.resourceUrl, commEvtPposType, { observe: 'response' });
  }

  update(commEvtPposType: ICommEvtPposType): Observable<EntityResponseType> {
    return this.http.put<ICommEvtPposType>(this.resourceUrl, commEvtPposType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICommEvtPposType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICommEvtPposType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
