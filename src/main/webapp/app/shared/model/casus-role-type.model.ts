export interface ICasusRoleType {
  id?: number;
}

export class CasusRoleType implements ICasusRoleType {
  constructor(public id?: number) {}
}
