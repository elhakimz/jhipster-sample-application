import { Moment } from 'moment';
import { IPartyRelationship } from 'app/shared/model/party-relationship.model';
import { IContactMechType } from 'app/shared/model/contact-mech-type.model';
import { ICasus } from 'app/shared/model/casus.model';

export interface ICommEvent {
  id?: number;
  eventId?: string;
  started?: Moment;
  ended?: Moment;
  note?: string;
  contextOf?: IPartyRelationship;
  occursVia?: IContactMechType;
  casus?: ICasus;
}

export class CommEvent implements ICommEvent {
  constructor(
    public id?: number,
    public eventId?: string,
    public started?: Moment,
    public ended?: Moment,
    public note?: string,
    public contextOf?: IPartyRelationship,
    public occursVia?: IContactMechType,
    public casus?: ICasus
  ) {}
}
