import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRentalSummariesforhimComponent } from './admin-rental-summariesforhim.component';

describe('AdminRentalSummariesforhimComponent', () => {
  let component: AdminRentalSummariesforhimComponent;
  let fixture: ComponentFixture<AdminRentalSummariesforhimComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminRentalSummariesforhimComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminRentalSummariesforhimComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
