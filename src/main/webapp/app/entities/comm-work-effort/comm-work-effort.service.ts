import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICommWorkEffort } from 'app/shared/model/comm-work-effort.model';

type EntityResponseType = HttpResponse<ICommWorkEffort>;
type EntityArrayResponseType = HttpResponse<ICommWorkEffort[]>;

@Injectable({ providedIn: 'root' })
export class CommWorkEffortService {
  public resourceUrl = SERVER_API_URL + 'api/comm-work-efforts';

  constructor(protected http: HttpClient) {}

  create(commWorkEffort: ICommWorkEffort): Observable<EntityResponseType> {
    return this.http.post<ICommWorkEffort>(this.resourceUrl, commWorkEffort, { observe: 'response' });
  }

  update(commWorkEffort: ICommWorkEffort): Observable<EntityResponseType> {
    return this.http.put<ICommWorkEffort>(this.resourceUrl, commWorkEffort, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICommWorkEffort>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICommWorkEffort[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
