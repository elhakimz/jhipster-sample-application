import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPartyContactMechPpos } from 'app/shared/model/party-contact-mech-ppos.model';

type EntityResponseType = HttpResponse<IPartyContactMechPpos>;
type EntityArrayResponseType = HttpResponse<IPartyContactMechPpos[]>;

@Injectable({ providedIn: 'root' })
export class PartyContactMechPposService {
  public resourceUrl = SERVER_API_URL + 'api/party-contact-mech-ppos';

  constructor(protected http: HttpClient) {}

  create(partyContactMechPpos: IPartyContactMechPpos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyContactMechPpos);
    return this.http
      .post<IPartyContactMechPpos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partyContactMechPpos: IPartyContactMechPpos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyContactMechPpos);
    return this.http
      .put<IPartyContactMechPpos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartyContactMechPpos>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartyContactMechPpos[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(partyContactMechPpos: IPartyContactMechPpos): IPartyContactMechPpos {
    const copy: IPartyContactMechPpos = Object.assign({}, partyContactMechPpos, {
      fromDate:
        partyContactMechPpos.fromDate && partyContactMechPpos.fromDate.isValid()
          ? partyContactMechPpos.fromDate.format(DATE_FORMAT)
          : undefined,
      thruDate:
        partyContactMechPpos.thruDate && partyContactMechPpos.thruDate.isValid()
          ? partyContactMechPpos.thruDate.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fromDate = res.body.fromDate ? moment(res.body.fromDate) : undefined;
      res.body.thruDate = res.body.thruDate ? moment(res.body.thruDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((partyContactMechPpos: IPartyContactMechPpos) => {
        partyContactMechPpos.fromDate = partyContactMechPpos.fromDate ? moment(partyContactMechPpos.fromDate) : undefined;
        partyContactMechPpos.thruDate = partyContactMechPpos.thruDate ? moment(partyContactMechPpos.thruDate) : undefined;
      });
    }
    return res;
  }
}
