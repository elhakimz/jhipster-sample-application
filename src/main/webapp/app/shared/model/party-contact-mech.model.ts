import { Moment } from 'moment';
import { IParty } from 'app/shared/model/party.model';
import { IContactMech } from 'app/shared/model/contact-mech.model';

export interface IPartyContactMech {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  nonSolicitation?: boolean;
  party?: IParty;
  contactMechanism?: IContactMech;
}

export class PartyContactMech implements IPartyContactMech {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public nonSolicitation?: boolean,
    public party?: IParty,
    public contactMechanism?: IContactMech
  ) {
    this.nonSolicitation = this.nonSolicitation || false;
  }
}
