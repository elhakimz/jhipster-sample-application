import { Moment } from 'moment';
import { IParty } from 'app/shared/model/party.model';
import { IdentificationType } from 'app/shared/model/enumerations/identification-type.model';

export interface IPartyIdentification {
  id?: number;
  identificationType?: IdentificationType;
  identNo?: string;
  validDate?: Moment;
  party?: IParty;
}

export class PartyIdentification implements IPartyIdentification {
  constructor(
    public id?: number,
    public identificationType?: IdentificationType,
    public identNo?: string,
    public validDate?: Moment,
    public party?: IParty
  ) {}
}
