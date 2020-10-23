export interface IRoleType {
  id?: number;
  name?: string;
  description?: string;
}

export class RoleType implements IRoleType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
