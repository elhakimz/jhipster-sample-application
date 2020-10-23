export interface IStatusType {
  id?: number;
  code?: string;
  name?: string;
}

export class StatusType implements IStatusType {
  constructor(public id?: number, public code?: string, public name?: string) {}
}
