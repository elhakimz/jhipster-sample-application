import { Moment } from 'moment';
import { IPartyContactMech } from 'app/shared/model/party-contact-mech.model';
import { IContactMechPposType } from 'app/shared/model/contact-mech-ppos-type.model';

export interface IPartyContactMechPpos {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  partyContactMechanism?: IPartyContactMech;
  contactMechanismPurposeType?: IContactMechPposType;
}

export class PartyContactMechPpos implements IPartyContactMechPpos {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public partyContactMechanism?: IPartyContactMech,
    public contactMechanismPurposeType?: IContactMechPposType
  ) {}
}
