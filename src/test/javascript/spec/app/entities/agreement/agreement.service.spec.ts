import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AgreementService } from 'app/entities/agreement/agreement.service';
import { IAgreement, Agreement } from 'app/shared/model/agreement.model';

describe('Service Tests', () => {
  describe('Agreement Service', () => {
    let injector: TestBed;
    let service: AgreementService;
    let httpMock: HttpTestingController;
    let elemDefault: IAgreement;
    let expectedResult: IAgreement | IAgreement[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AgreementService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Agreement(0, 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            agreementDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Agreement', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            agreementDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            agreementDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Agreement()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Agreement', () => {
        const returnedFromService = Object.assign(
          {
            agreementNo: 'BBBBBB',
            agreementDate: currentDate.format(DATE_FORMAT),
            name: 'BBBBBB',
            description: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            agreementDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Agreement', () => {
        const returnedFromService = Object.assign(
          {
            agreementNo: 'BBBBBB',
            agreementDate: currentDate.format(DATE_FORMAT),
            name: 'BBBBBB',
            description: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            agreementDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Agreement', () => {
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
