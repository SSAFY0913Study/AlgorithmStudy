#include <iostream>
#include <vector>
#include <algorithm>
#include <string.h>
#include <string>
#include <deque>
using namespace std;

int n, w, L, times = 1, curWeight = 0;
vector<int> weight;
vector<int> bridge;

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	cin >> n >> w >> L;

	weight = vector<int>(n + 1, 0);
	bridge = vector<int>(w + 1, 0);

	for (int i = 1; i <= n; i++) {
		cin >> weight[i];
	}

	bridge[1] = weight[1];	// 다리의 첫번째 구간에 트럭의 무게를 적는다.
	curWeight += weight[1];
	times = 1;

	int curTruckIdx = 2;	// 다음 트럭의 번호

	while (curTruckIdx <= n) {	// 마지막 트럭이 다리의 시작점에 오르면 종료
		times++; // 시간 증가

		curWeight -= bridge[w];

		for (int i = w - 1; i >= 1; i--) {
			bridge[i + 1] = bridge[i];	// 트럭을 한칸씩 이동
		}

		if (curWeight + weight[curTruckIdx] <= L) {	// 현재 다리위에 있는 트럭의 무게와 다음 트럭의 무게의 합이 L보다 작으면 다음 트럭을 다리의 시작점에 올림
			curWeight += weight[curTruckIdx];
			bridge[1] = weight[curTruckIdx];	// 다리의 시작점에 트럭을 올림
			curTruckIdx++;
		}
		else bridge[1] = 0;
	}

	cout << times + w;	// 마지막 트럭이 올라간 시점과 총 다리의 길이를 더한 것이 결과값

	return 0;
}