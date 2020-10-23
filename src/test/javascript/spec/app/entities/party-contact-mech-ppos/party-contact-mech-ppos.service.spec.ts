import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PartyContactMechPposService } from 'app/entities/party-contact-mech-ppos/party-contact-mech-ppos.service';
import { IPartyContactMechPpos, PartyContactMechPpos } from 'app/shared/model/party-contact-mech-ppos.model';

describe('Service Tests', () => {
  describe('PartyContactMechPpos Service', () => {
    let injector: TestBed;
    let service: PartyContactMechPposService;
    let httpMock: HttpTestingController;
    let elemDefault: IPartyContactMechPpos;
    let expectedResult: IPartyContactMechPpos | IPartyContactMechPpos[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PartyContactMechPposService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PartyContactMechPpos(0, currentDate, currentDate);
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

      it('should create a PartyContactMechPpos', () => {
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

        service.create(new PartyContactMechPpos()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PartyContactMechPpos', () => {
        const returnedFromService = Object.assign(
          {
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

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PartyContactMechPpos', () => {
        const returnedFromService = Object.assign(
          {
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

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PartyContactMechPpos', () => {
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
