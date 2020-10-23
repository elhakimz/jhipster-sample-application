import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICommEventPurpose } from 'app/shared/model/comm-event-purpose.model';

type EntityResponseType = HttpResponse<ICommEventPurpose>;
type EntityArrayResponseType = HttpResponse<ICommEventPurpose[]>;

@Injectable({ providedIn: 'root' })
export class CommEventPurposeService {
  public resourceUrl = SERVER_API_URL + 'api/comm-event-purposes';

  constructor(protected http: HttpClient) {}

  create(commEventPurpose: ICommEventPurpose): Observable<EntityResponseType> {
    return this.http.post<ICommEventPurpose>(this.resourceUrl, commEventPurpose, { observe: 'response' });
  }

  update(commEventPurpose: ICommEventPurpose): Observable<EntityResponseType> {
    return this.http.put<ICommEventPurpose>(this.resourceUrl, commEventPurpose, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICommEventPurpose>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICommEventPurpose[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
