export interface IContactMechPposType {
  id?: number;
  code?: string;
  description?: string;
}

export class ContactMechPposType implements IContactMechPposType {
  constructor(public id?: number, public code?: string, public description?: string) {}
}
