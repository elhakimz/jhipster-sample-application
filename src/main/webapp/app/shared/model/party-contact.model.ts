import { IParty } from 'app/shared/model/party.model';
import { ContactType } from 'app/shared/model/enumerations/contact-type.model';

export interface IPartyContact {
  id?: number;
  contactType?: ContactType;
  number?: string;
  primary?: boolean;
  party?: IParty;
}

export class PartyContact implements IPartyContact {
  constructor(
    public id?: number,
    public contactType?: ContactType,
    public number?: string,
    public primary?: boolean,
    public party?: IParty
  ) {
    this.primary = this.primary || false;
  }
}
