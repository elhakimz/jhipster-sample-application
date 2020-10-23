import { Moment } from 'moment';
import { IParty } from 'app/shared/model/party.model';
import { PartyClassType } from 'app/shared/model/enumerations/party-class-type.model';

export interface IPartyClassification {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  partyClassType?: PartyClassType;
  party?: IParty;
}

export class PartyClassification implements IPartyClassification {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public partyClassType?: PartyClassType,
    public party?: IParty
  ) {}
}
