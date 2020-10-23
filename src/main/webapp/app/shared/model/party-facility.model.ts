import { IParty } from 'app/shared/model/party.model';
import { IFacility } from 'app/shared/model/facility.model';

export interface IPartyFacility {
  id?: number;
  party?: IParty;
  facility?: IFacility;
}

export class PartyFacility implements IPartyFacility {
  constructor(public id?: number, public party?: IParty, public facility?: IFacility) {}
}
