#include<iostream>
#include<string>
#include<vector>
#include<cstring>
#include<algorithm>
#include<queue>
#include<utility>
#include<cmath>
using namespace std;
/*
1. �Է¹ޱ�

2. bfs�� ��ü ��ȸ�ϸ鼭 ������ ���� Ȯ�� �� ������ �� üũ
2-1. ���պ� �α� ����, ���տ� �����ִ� ���� �� üũ

3. ���� ���� üũ�ϸ鼭 �α� �̵� ���� 

4. ���� �ݺ��ϸ鼭 (���ո��� �ο� �й� ��������, ������ ���� n*n�̸�) stop
*/
typedef pair<int, int>p;
int dx[4] = { -1,1,0,0 }, dy[4] = { 0,0,-1,1 };
int n, mini, maxi, ans, sum;
int arr[50][50], check[50][50];
bool flag;
queue<p>q;
vector<vector<p>>posv;
vector<int>sumv;
void input() {
	cin >> n >> mini >> maxi;
	for (int i = 0; i < n; i++)for (int j = 0; j < n; j++)cin >> arr[i][j];
}
bool canMove(int a, int b) {
	return mini <= abs(a - b) && abs(a - b) <= maxi;
}
void bfs(int tmp,int s) {
	int count = 1;
	while (!q.empty()) {
		int x = q.front().first, y = q.front().second;
		q.pop();
		for (int i = 0; i < 4; i++) {
			int nx = x + dx[i], ny = y + dy[i];
			if (nx<0 || nx>n - 1 || ny<0 || ny>n - 1||check[nx][ny]|| !canMove(arr[x][y], arr[nx][ny]))continue;//������ �ʰ� Ȥ�� �湮�ߴٸ� Ȥ�� ���̰� min~maxi���� �ƴ϶��
			check[nx][ny] = tmp;
			q.push({ nx,ny });
			s += arr[nx][ny]; //���պ� ������ �α� �� �� ���ϱ�
			posv[tmp - 1].push_back({ nx,ny });//���պ� ��ǥ ����
		}
	}
	sumv.push_back(s);
}
int search() { //�α��̵� ��, bfs�� ���� ���� ���� Ȯ��
	int connectionCnt = 0;
	posv.clear();
	sumv.clear();
	posv.resize(n * n);
	memset(check, 0, sizeof(check));
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if (check[i][j])continue;
			q.push({ i,j });
			check[i][j] = ++connectionCnt;
			bfs(connectionCnt, arr[i][j]); //bfs�� ���� �� ������ �α� ��, ���Ἲ�� ���� ���(����)���� ī����
			posv[connectionCnt - 1].push_back({ i,j });
		}
	}
	return connectionCnt; //������ �� return
}
int move(int conn) { 
	if (conn == n * n)return 0; //������ ���� n*n�̸�(=��ΰ� �̵��� �� ���� �����̸�) return 0
	int cnt = 0;
	for (int i = 0; i < conn; i++) {
		int s = posv[i].size();
		int tmp = sumv[i] / s; //������ �α���/������ ���� ��
		for (int j = 0; j < s; j++) {
			int x = posv[i][j].first, y = posv[i][j].second;
			if (arr[x][y] == tmp) { //�̹� ����α��� ���� ���� �� ī����
				cnt++;
			}
			arr[x][y] = tmp; //�α� ����ȭ
		}
	}
	ans++; //�α� �̵� Ƚ��
	if (cnt == n * n) return 0;//�̹� n*n���� ���� ��� ����ȭ�� �Ǿ��ִٸ� return 0;
	return 1;
}
int main() {
	ios_base::sync_with_stdio(false), cin.tie(0), cout.tie(0);
	input();
	while (move(search())) {} //���� ���� Ž�� + �α��̵�
	cout << ans << "\n";
	return 0;
}

