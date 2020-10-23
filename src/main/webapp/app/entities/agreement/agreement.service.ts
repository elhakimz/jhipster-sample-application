import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAgreement } from 'app/shared/model/agreement.model';

type EntityResponseType = HttpResponse<IAgreement>;
type EntityArrayResponseType = HttpResponse<IAgreement[]>;

@Injectable({ providedIn: 'root' })
export class AgreementService {
  public resourceUrl = SERVER_API_URL + 'api/agreements';

  constructor(protected http: HttpClient) {}

  create(agreement: IAgreement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agreement);
    return this.http
      .post<IAgreement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(agreement: IAgreement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agreement);
    return this.http
      .put<IAgreement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAgreement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAgreement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(agreement: IAgreement): IAgreement {
    const copy: IAgreement = Object.assign({}, agreement, {
      agreementDate: agreement.agreementDate && agreement.agreementDate.isValid() ? agreement.agreementDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.agreementDate = res.body.agreementDate ? moment(res.body.agreementDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((agreement: IAgreement) => {
        agreement.agreementDate = agreement.agreementDate ? moment(agreement.agreementDate) : undefined;
      });
    }
    return res;
  }
}
