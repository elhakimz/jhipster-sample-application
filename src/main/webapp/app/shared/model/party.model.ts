import { Moment } from 'moment';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { PartyType } from 'app/shared/model/enumerations/party-type.model';

export interface IParty {
  id?: number;
  name?: string;
  firstName?: string;
  lastName?: string;
  birthDate?: Moment;
  birthPlace?: string;
  gender?: Gender;
  partyType?: PartyType;
}

export class Party implements IParty {
  constructor(
    public id?: number,
    public name?: string,
    public firstName?: string,
    public lastName?: string,
    public birthDate?: Moment,
    public birthPlace?: string,
    public gender?: Gender,
    public partyType?: PartyType
  ) {}
}
