import { Moment } from 'moment';
import { IPartyRelationship } from 'app/shared/model/party-relationship.model';

export interface IAgreement {
  id?: number;
  agreementNo?: string;
  agreementDate?: Moment;
  name?: string;
  description?: string;
  partyRelationship?: IPartyRelationship;
}

export class Agreement implements IAgreement {
  constructor(
    public id?: number,
    public agreementNo?: string,
    public agreementDate?: Moment,
    public name?: string,
    public description?: string,
    public partyRelationship?: IPartyRelationship
  ) {}
}
