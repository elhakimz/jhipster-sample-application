export interface IFacilityRoleType {
  id?: number;
  code?: string;
  description?: string;
}

export class FacilityRoleType implements IFacilityRoleType {
  constructor(public id?: number, public code?: string, public description?: string) {}
}
