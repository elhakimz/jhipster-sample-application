import { IParty } from 'app/shared/model/party.model';
import { IFacilityRoleType } from 'app/shared/model/facility-role-type.model';
import { IFacility } from 'app/shared/model/facility.model';

export interface IFacilityRole {
  id?: number;
  party?: IParty;
  facilityRoleType?: IFacilityRoleType;
  facility?: IFacility;
}

export class FacilityRole implements IFacilityRole {
  constructor(public id?: number, public party?: IParty, public facilityRoleType?: IFacilityRoleType, public facility?: IFacility) {}
}
