import { FacilityType } from 'app/shared/model/enumerations/facility-type.model';

export interface IFacility {
  id?: number;
  code?: string;
  description?: string;
  area?: number;
  facilityType?: FacilityType;
  parent?: IFacility;
}

export class Facility implements IFacility {
  constructor(
    public id?: number,
    public code?: string,
    public description?: string,
    public area?: number,
    public facilityType?: FacilityType,
    public parent?: IFacility
  ) {}
}
