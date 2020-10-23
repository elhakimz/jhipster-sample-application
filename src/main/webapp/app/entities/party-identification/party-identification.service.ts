import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPartyIdentification } from 'app/shared/model/party-identification.model';

type EntityResponseType = HttpResponse<IPartyIdentification>;
type EntityArrayResponseType = HttpResponse<IPartyIdentification[]>;

@Injectable({ providedIn: 'root' })
export class PartyIdentificationService {
  public resourceUrl = SERVER_API_URL + 'api/party-identifications';

  constructor(protected http: HttpClient) {}

  create(partyIdentification: IPartyIdentification): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyIdentification);
    return this.http
      .post<IPartyIdentification>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partyIdentification: IPartyIdentification): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyIdentification);
    return this.http
      .put<IPartyIdentification>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartyIdentification>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartyIdentification[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(partyIdentification: IPartyIdentification): IPartyIdentification {
    const copy: IPartyIdentification = Object.assign({}, partyIdentification, {
      validDate:
        partyIdentification.validDate && partyIdentification.validDate.isValid()
          ? partyIdentification.validDate.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.validDate = res.body.validDate ? moment(res.body.validDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((partyIdentification: IPartyIdentification) => {
        partyIdentification.validDate = partyIdentification.validDate ? moment(partyIdentification.validDate) : undefined;
      });
    }
    return res;
  }
}
