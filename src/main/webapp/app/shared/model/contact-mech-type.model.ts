export interface IContactMechType {
  id?: number;
  code?: string;
  name?: string;
}

export class ContactMechType implements IContactMechType {
  constructor(public id?: number, public code?: string, public name?: string) {}
}
