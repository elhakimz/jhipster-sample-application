import { ICasusRoleType } from 'app/shared/model/casus-role-type.model';

export interface ICasusRole {
  id?: number;
  roleType?: ICasusRoleType;
}

export class CasusRole implements ICasusRole {
  constructor(public id?: number, public roleType?: ICasusRoleType) {}
}
