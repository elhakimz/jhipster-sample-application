import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PartyRelationshipService } from 'app/entities/party-relationship/party-relationship.service';
import { IPartyRelationship, PartyRelationship } from 'app/shared/model/party-relationship.model';
import { PartyRelType } from 'app/shared/model/enumerations/party-rel-type.model';
import { PriorityType } from 'app/shared/model/enumerations/priority-type.model';

describe('Service Tests', () => {
  describe('PartyRelationship Service', () => {
    let injector: TestBed;
    let service: PartyRelationshipService;
    let httpMock: HttpTestingController;
    let elemDefault: IPartyRelationship;
    let expectedResult: IPartyRelationship | IPartyRelationship[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PartyRelationshipService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PartyRelationship(0, PartyRelType.EMPLOYMENT, currentDate, currentDate, 'AAAAAAA', 'AAAAAAA', PriorityType.HIGH);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fromDate: currentDate.format(DATE_FORMAT),
            thruDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PartyRelationship', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fromDate: currentDate.format(DATE_FORMAT),
            thruDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            thruDate: currentDate,
          },
          returnedFromService
        );

        service.create(new PartyRelationship()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PartyRelationship', () => {
        const returnedFromService = Object.assign(
          {
            partyRelationshipType: 'BBBBBB',
            fromDate: currentDate.format(DATE_FORMAT),
            thruDate: currentDate.format(DATE_FORMAT),
            name: 'BBBBBB',
            description: 'BBBBBB',
            priorityType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            thruDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PartyRelationship', () => {
        const returnedFromService = Object.assign(
          {
            partyRelationshipType: 'BBBBBB',
            fromDate: currentDate.format(DATE_FORMAT),
            thruDate: currentDate.format(DATE_FORMAT),
            name: 'BBBBBB',
            description: 'BBBBBB',
            priorityType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            thruDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PartyRelationship', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
