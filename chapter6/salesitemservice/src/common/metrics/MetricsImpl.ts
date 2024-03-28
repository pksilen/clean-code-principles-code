import { Injectable } from '@nestjs/common';
import { Metrics } from './Metrics';

@Injectable()
export default class MetricsImpl implements Metrics {
  private readonly labelToCount = new Map<string, number>();

  incrementRequestCounter(label: string): void {
    this.labelToCount.set(label, (this.labelToCount.get(label) ?? 0) + 1);
    // In real-life you would use a metrics library like Prometheus client here
    console.log(
      `Request count for endpoint ${label} is ${this.labelToCount.get(label)}`,
    );
  }
}
