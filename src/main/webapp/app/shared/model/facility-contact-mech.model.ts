import { IContactMech } from 'app/shared/model/contact-mech.model';
import { IFacility } from 'app/shared/model/facility.model';
import { ICommEvent } from 'app/shared/model/comm-event.model';

export interface IFacilityContactMech {
  id?: number;
  contactMechanism?: IContactMech;
  facility?: IFacility;
  communicationEvent?: ICommEvent;
}

export class FacilityContactMech implements IFacilityContactMech {
  constructor(
    public id?: number,
    public contactMechanism?: IContactMech,
    public facility?: IFacility,
    public communicationEvent?: ICommEvent
  ) {}
}
