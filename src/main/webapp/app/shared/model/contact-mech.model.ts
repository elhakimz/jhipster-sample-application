import { IContactMechType } from 'app/shared/model/contact-mech-type.model';

export interface IContactMech {
  id?: number;
  code?: string;
  areaCode?: string;
  countryCode?: string;
  contactNumber?: string;
  address1?: string;
  address2?: string;
  directions?: string;
  electronicAddress?: string;
  contactMechanismType?: IContactMechType;
}

export class ContactMech implements IContactMech {
  constructor(
    public id?: number,
    public code?: string,
    public areaCode?: string,
    public countryCode?: string,
    public contactNumber?: string,
    public address1?: string,
    public address2?: string,
    public directions?: string,
    public electronicAddress?: string,
    public contactMechanismType?: IContactMechType
  ) {}
}
