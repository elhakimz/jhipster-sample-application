import { Moment } from 'moment';
import { IPartyRole } from 'app/shared/model/party-role.model';
import { IStatusType } from 'app/shared/model/status-type.model';
import { PartyRelType } from 'app/shared/model/enumerations/party-rel-type.model';
import { PriorityType } from 'app/shared/model/enumerations/priority-type.model';

export interface IPartyRelationship {
  id?: number;
  partyRelationshipType?: PartyRelType;
  fromDate?: Moment;
  thruDate?: Moment;
  name?: string;
  description?: string;
  priorityType?: PriorityType;
  fromPartyRole?: IPartyRole;
  toPartyRole?: IPartyRole;
  statusType?: IStatusType;
}

export class PartyRelationship implements IPartyRelationship {
  constructor(
    public id?: number,
    public partyRelationshipType?: PartyRelType,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public name?: string,
    public description?: string,
    public priorityType?: PriorityType,
    public fromPartyRole?: IPartyRole,
    public toPartyRole?: IPartyRole,
    public statusType?: IStatusType
  ) {}
}
