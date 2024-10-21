import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductobusqComponent } from './productobusq.component';

describe('ProductobusqComponent', () => {
  let component: ProductobusqComponent;
  let fixture: ComponentFixture<ProductobusqComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductobusqComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductobusqComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
