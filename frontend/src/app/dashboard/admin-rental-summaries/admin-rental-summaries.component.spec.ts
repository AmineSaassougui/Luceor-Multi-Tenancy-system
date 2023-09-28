import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRentalSummariesComponent } from './admin-rental-summaries.component';

describe('AdminRentalSummariesComponent', () => {
  let component: AdminRentalSummariesComponent;
  let fixture: ComponentFixture<AdminRentalSummariesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminRentalSummariesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminRentalSummariesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
