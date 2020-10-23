import { IParty } from 'app/shared/model/party.model';

export interface IPartyAddress {
  id?: number;
  addressLine1?: string;
  addressLine2?: string;
  city?: string;
  party?: IParty;
}

export class PartyAddress implements IPartyAddress {
  constructor(
    public id?: number,
    public addressLine1?: string,
    public addressLine2?: string,
    public city?: string,
    public party?: IParty
  ) {}
}
