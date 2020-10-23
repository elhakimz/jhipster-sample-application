import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPartyRelationship } from 'app/shared/model/party-relationship.model';

type EntityResponseType = HttpResponse<IPartyRelationship>;
type EntityArrayResponseType = HttpResponse<IPartyRelationship[]>;

@Injectable({ providedIn: 'root' })
export class PartyRelationshipService {
  public resourceUrl = SERVER_API_URL + 'api/party-relationships';

  constructor(protected http: HttpClient) {}

  create(partyRelationship: IPartyRelationship): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyRelationship);
    return this.http
      .post<IPartyRelationship>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partyRelationship: IPartyRelationship): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyRelationship);
    return this.http
      .put<IPartyRelationship>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartyRelationship>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartyRelationship[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(partyRelationship: IPartyRelationship): IPartyRelationship {
    const copy: IPartyRelationship = Object.assign({}, partyRelationship, {
      fromDate:
        partyRelationship.fromDate && partyRelationship.fromDate.isValid() ? partyRelationship.fromDate.format(DATE_FORMAT) : undefined,
      thruDate:
        partyRelationship.thruDate && partyRelationship.thruDate.isValid() ? partyRelationship.thruDate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((partyRelationship: IPartyRelationship) => {
        partyRelationship.fromDate = partyRelationship.fromDate ? moment(partyRelationship.fromDate) : undefined;
        partyRelationship.thruDate = partyRelationship.thruDate ? moment(partyRelationship.thruDate) : undefined;
      });
    }
    return res;
  }
}
