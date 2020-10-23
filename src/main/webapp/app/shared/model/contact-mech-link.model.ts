import { IContactMech } from 'app/shared/model/contact-mech.model';

export interface IContactMechLink {
  id?: number;
  toContactMechanism?: IContactMech;
  fromContactMechanism?: IContactMech;
}

export class ContactMechLink implements IContactMechLink {
  constructor(public id?: number, public toContactMechanism?: IContactMech, public fromContactMechanism?: IContactMech) {}
}
