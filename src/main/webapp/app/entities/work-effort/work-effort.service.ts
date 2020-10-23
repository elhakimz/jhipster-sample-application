import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWorkEffort } from 'app/shared/model/work-effort.model';

type EntityResponseType = HttpResponse<IWorkEffort>;
type EntityArrayResponseType = HttpResponse<IWorkEffort[]>;

@Injectable({ providedIn: 'root' })
export class WorkEffortService {
  public resourceUrl = SERVER_API_URL + 'api/work-efforts';

  constructor(protected http: HttpClient) {}

  create(workEffort: IWorkEffort): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workEffort);
    return this.http
      .post<IWorkEffort>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(workEffort: IWorkEffort): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workEffort);
    return this.http
      .put<IWorkEffort>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWorkEffort>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWorkEffort[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(workEffort: IWorkEffort): IWorkEffort {
    const copy: IWorkEffort = Object.assign({}, workEffort, {
      scheduledStart:
        workEffort.scheduledStart && workEffort.scheduledStart.isValid() ? workEffort.scheduledStart.format(DATE_FORMAT) : undefined,
      scheduledCompletion:
        workEffort.scheduledCompletion && workEffort.scheduledCompletion.isValid()
          ? workEffort.scheduledCompletion.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.scheduledStart = res.body.scheduledStart ? moment(res.body.scheduledStart) : undefined;
      res.body.scheduledCompletion = res.body.scheduledCompletion ? moment(res.body.scheduledCompletion) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((workEffort: IWorkEffort) => {
        workEffort.scheduledStart = workEffort.scheduledStart ? moment(workEffort.scheduledStart) : undefined;
        workEffort.scheduledCompletion = workEffort.scheduledCompletion ? moment(workEffort.scheduledCompletion) : undefined;
      });
    }
    return res;
  }
}
