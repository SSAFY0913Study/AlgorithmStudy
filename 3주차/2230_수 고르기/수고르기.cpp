#include <iostream>
#include <vector>
#include <algorithm>
#include <limits.h>
using namespace std;

int N, M, result = INT_MAX;
vector<int> arr;


int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	cin >> N >> M;

	arr = vector<int>(N);

	for (int i = 0; i < N; i++) {
		cin >> arr[i];
	}

	sort(arr.begin(), arr.end()); //입력받은 수들을 오름차순으로 진행한다.

	/* 풀이법1 
	for (int i = 0; i < N - 1; i++) {	//첫번째 원소부터 N - 1번째 원소까지 반복
		for (int j = i + 1; j < N; j++) {//자신의 뒤의 원소와 값을 비교한다.
			if (arr[j] - arr[i] >= M) {// 뒤의 원소와 차이가 M보다 크거나 같아지는 순간 반복문을 탈출한다.
				if (arr[j] - arr[i] == M) {//차이가 M이면 M이 최선의 결과이므로 결과를 출력하고 종료한다.
					cout << M;
					return 0;
				}
				result = min(result, arr[j] - arr[i]);	// 현재 최소값과 비교하여 결과를 갱신하고 반목문을 탈출한다.
				break;
			}
		}
	}*/

	//풀이법2 이분탐색을 활용한 방법
	for (int i = 0; i < N - 1; i++) {
		int left = i + 1;
		int right = N - 1;
		int mid;	//mid인덱스

		while (left <= right) {
			mid = (left + right) / 2;
			if (arr[mid] - arr[i] > M) {//차이가 M보다 크면 right위치 조정
				result = min(result, arr[mid] - arr[i]);	//결과값 갱신
				right = mid - 1;
			}
			else if (arr[mid] - arr[i] < M) {
				left = mid + 1;
			}
			else {	//차이가 M인경우 결과를 출력하고 종료
				cout << M;
				return 0;
			}
		}
	}

	cout << result;

	return 0;
}