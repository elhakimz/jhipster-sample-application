import { IParty } from 'app/shared/model/party.model';
import { IRoleType } from 'app/shared/model/role-type.model';

export interface IPartyRole {
  id?: number;
  name?: string;
  party?: IParty;
  roleType?: IRoleType;
}

export class PartyRole implements IPartyRole {
  constructor(public id?: number, public name?: string, public party?: IParty, public roleType?: IRoleType) {}
}
