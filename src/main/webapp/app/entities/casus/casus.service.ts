import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICasus } from 'app/shared/model/casus.model';

type EntityResponseType = HttpResponse<ICasus>;
type EntityArrayResponseType = HttpResponse<ICasus[]>;

@Injectable({ providedIn: 'root' })
export class CasusService {
  public resourceUrl = SERVER_API_URL + 'api/casuses';

  constructor(protected http: HttpClient) {}

  create(casus: ICasus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(casus);
    return this.http
      .post<ICasus>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(casus: ICasus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(casus);
    return this.http
      .put<ICasus>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICasus>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICasus[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(casus: ICasus): ICasus {
    const copy: ICasus = Object.assign({}, casus, {
      start: casus.start && casus.start.isValid() ? casus.start.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.start = res.body.start ? moment(res.body.start) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((casus: ICasus) => {
        casus.start = casus.start ? moment(casus.start) : undefined;
      });
    }
    return res;
  }
}
