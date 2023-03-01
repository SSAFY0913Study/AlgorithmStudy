#include <iostream>
#include <vector>
#include <algorithm>
#include <limits.h>
using namespace std;


/* 풀이방법
* 1. 3차원 배열을 통해 A, B, C물통에 담긴 물의 양이 이전에 방문했던 동일한 양인지 체크한다.
* 2. 이전에 방문했던 위치면 재귀호출을 진행하지 않는다.
* 3. A물통의 양이 0일때 C의 양을 결과값에 저장한다.
*/

int capacity[4];
bool map[200][200][200];
vector<int> result;	//A물통의 양이 0일때 C 물통에 있는 물의 양을 저장할 배열

void moveWater(int a, int b, int c) {// a= 현재 A물통의 양, b = 현재 B물통의 양, c = 현재 C물통의 양
	if (a == 0) result.push_back(c);	//A물통이 비었을 때 C물통의 물의 양을 결과 배열에 추가
	int moveAmount;	// 이동할 물의 양
	if (a != 0) {//A물통에서 B나 C물통으로 물을 옮기는 경우
		//1. A물통에서 B물통으로 옮기는 경우
		moveAmount = min(a, capacity[2] - b);	// 이동할 물의 양은 A물통에 있는 물의 양과 B물통의 용량 - 현재 B물통에 있는 물의 양 중 최소값
		if (!map[a - moveAmount][b + moveAmount][c]) {// 다음 물통상태를 방문한 적이 없다면 재귀 진행
			map[a - moveAmount][b + moveAmount][c] = true;	//방문체크
			moveWater(a - moveAmount, b + moveAmount, c);
		}

		//2. A물통에서 C물통으로 옮기는 경우
		moveAmount = min(a, capacity[3] - c);
		if (!map[a - moveAmount][b][c + moveAmount]) {
			map[a - moveAmount][b][c + moveAmount] = true;
			moveWater(a - moveAmount, b, c + moveAmount);
		}
	}
	if (b != 0) {
		//3. B물통에서 A물통으로 옮기는 경우
		moveAmount = min(b, capacity[1] - a);
		if (!map[a + moveAmount][b - moveAmount][c]) {
			map[a + moveAmount][b - moveAmount][c] = true;
			moveWater(a + moveAmount, b - moveAmount, c);
		}
		//4. B물통에서 C물통으로 옮기는 경우
		moveAmount = min(b, capacity[3] - c);
		if (!map[a][b - moveAmount][c + moveAmount]) {
			map[a][b - moveAmount][c + moveAmount] = true;
			moveWater(a, b - moveAmount, c + moveAmount);
		}
	}
	if (c != 0) {
		//5. C물통에서 A물통으로 옮기는 경우
		moveAmount = min(c, capacity[1] - a);
		if (!map[a + moveAmount][b][c - moveAmount]) {
			map[a + moveAmount][b][c - moveAmount] = true;
			moveWater(a + moveAmount, b, c - moveAmount);
		}
		//6. C물통에서 B물통으로 옮기는 경우
		moveAmount = min(c, capacity[2] - b);
		if (!map[a][b + moveAmount][c - moveAmount]) {
			map[a][b + moveAmount][c - moveAmount] = true;
			moveWater(a, b + moveAmount, c - moveAmount);
		}
	}
}

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	for (int i = 1; i <= 3; i++) {
		cin >> capacity[i];
	}

	map[0][0][capacity[3]] = true;
	moveWater(0, 0, capacity[3]);

	sort(result.begin(), result.end());	// 결과값을 오름차순으로 정렬

	for (int i = 0; i < result.size(); i++) {
		cout << result[i] << ' ';
	}

	return 0;
}